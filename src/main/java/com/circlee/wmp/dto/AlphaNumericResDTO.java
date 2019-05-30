package com.circlee.wmp.dto;

import com.circlee.wmp.common.enums.AlphaNumericType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

@Data
public class AlphaNumericResDTO {

    private String divideValue;
    private String remainValue;

    private AlphaNumericResDTO() {}

    public AlphaNumericResDTO(Map<AlphaNumericType, TreeMap<Character, Long>> map , BigDecimal mok) {

        BigDecimal length = map.entrySet()
                .stream()
                .map( (es) -> es.getValue())
                .flatMap(mapStream -> mapStream.entrySet().stream())
                .map(entry -> entry.getValue())
                .reduce( BigDecimal.ZERO, (bd, l) -> { return bd.add(new BigDecimal(l));}, (b1,b2) -> { return b1.add(b2);});
        ;


        BigDecimal divideCnt = length.divide(mok, BigDecimal.ROUND_DOWN);
        BigDecimal divideValenLength = divideCnt.multiply(mok);

        LinkedList<TreeMap<Character, Long>> linkedlist = new LinkedList<TreeMap<Character, Long>>();

        linkedlist.add(map.get(AlphaNumericType.ALPHABET));
        linkedlist.add(map.get(AlphaNumericType.NUMBER));


        StringBuilder divideValueBuilder = new StringBuilder();
        StringBuilder remainValueBuilder = new StringBuilder();


        BigDecimal checker = BigDecimal.ZERO;

        while(linkedlist.size() != 0) {

            TreeMap<Character, Long> tree = linkedlist.remove();

            Map.Entry<Character, Long> entry = tree.firstEntry();
            Character c = entry.getKey();
            long cnt = entry.getValue();
            long cntMinus = cnt -1;

            tree.put(c , cntMinus);

            if(tree.get(c) <= 0) {
                tree.remove(c);
            }

            if(tree.size() != 0) {
                linkedlist.add(tree);
            }

            //System.out.println(c);


            remainValueBuilder.append(c);

            checker = checker.add(BigDecimal.ONE);
            if(checker.compareTo(mok) == 0) {
                checker = BigDecimal.ZERO;

                divideValueBuilder.append(remainValueBuilder);
                remainValueBuilder.setLength(0);
            }
        }


        this.divideValue = divideValueBuilder.toString();
        this.remainValue = remainValueBuilder.toString();
    }
}
