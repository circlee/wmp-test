package com.circlee.wmp.service.impl;

import com.circlee.wmp.common.enums.AlphaNumericCondition;
import com.circlee.wmp.common.enums.AlphaNumericType;
import com.circlee.wmp.dto.AlphaNumericResDTO;
import com.circlee.wmp.service.AlphaNumericService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AlphaNumericServiceImpl implements AlphaNumericService {


    final static Supplier<Predicate<Character>> genTagFilter = () -> {
            final AtomicBoolean hasTag = new AtomicBoolean();
            hasTag.set(false);
            return (c) -> {
                if(c == '<') {
                    hasTag.set(true);
                    return false;
                } else if(c == '>') {
                    hasTag.set(false);
                    return false;
                }

                if(hasTag.get()) {
                    return false;
                }
                return true;
            };
    };

    final static Comparator<Character> alphaNumericComparator = (o1, o2) -> {
        if(Character.isAlphabetic(o1) && Character.isAlphabetic(o2)) {
            int i = Character.valueOf(Character.toUpperCase(o1))
                    .compareTo(Character.valueOf(Character.toUpperCase(o2)));
            if(i == 0) {
                return o1.compareTo(o2);
            }
            return i;
        }
        return o1.compareTo(o2);
    };

    @Override
    public Mono<AlphaNumericResDTO> getCharStreamFromOpenURL(String url, AlphaNumericCondition condition, BigDecimal mok){

        try(final BufferedReader br = new BufferedReader(new InputStreamReader(URI.create(url).toURL().openStream()))){


            CharactersIterator ci = new CharactersIterator(br);

            Stream<Character> charStream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(ci, Spliterator.ORDERED),false);


            Map<AlphaNumericType, TreeMap<Character, Long>> typeCountMap = charStreamOperator(charStream, condition);

            return Mono.just(makeAggregateResult(typeCountMap, mok));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Mono.empty();
    }


    private Map<AlphaNumericType, TreeMap<Character, Long>> charStreamOperator(Stream<Character> charStream , AlphaNumericCondition condition){

        Function<AlphaNumericCondition,Predicate<Character>> makeFilterBuCodition = (alphaNumericCondition) -> {
            if(AlphaNumericCondition.INCLUDE_TAG == alphaNumericCondition) {
                return (c) -> {return true;};
            } else {
                return genTagFilter.get();
            }
        };

        return charStream
                .filter(makeFilterBuCodition.apply(condition))
                //.filter(genTagFilter.get())
                .filter(AlphaNumericType::isAlphaNumericType)
                .collect(
                        Collectors.groupingBy(
                                AlphaNumericType::findAlphaNumericType
                                , Collectors.groupingBy(
                                        c -> c
                                        , ()-> { return new TreeMap<Character, Long>(alphaNumericComparator);}
                                        , Collectors.counting()
                                )
                        )
                );
    }

    private AlphaNumericResDTO makeAggregateResult(Map<AlphaNumericType, TreeMap<Character, Long>> map , BigDecimal mok){

        return new AlphaNumericResDTO(map, mok);
    }



    class CharactersIterator implements Iterator<Character> {

        private BufferedReader br;
        private LinkedList<Character> nextBuf;

        private boolean lastMark = false;

        public CharactersIterator(BufferedReader br){
            this.br = br;
            nextBuf = new LinkedList<>();
        }

        @Override
        public boolean hasNext() {
            if(lastMark) {
                 return false;
            }

            return true;
        }

        @Override
        public Character next() {

            int i;

            try {
                i = br.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(i == -1) {
                lastMark = true;
            }
            return (char) i;
        }
    }

}
