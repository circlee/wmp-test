# 사전과제 #

2.생태정보 서비스 API 개발

---

### 개발환경 ###

- Java 1.8.x
- Lombok plugin
- SpringBoot
- H2



---

### 문제해결 전략

###### 문제이해
- 서비스지역 과 지역프로그램은 다대다 관계로 생각되어짐.
- 생태 관광 정보의 서비스 지역정보가 정규화된 데이터로 보이지 않아. 최대한 시도/시구군 까지만 서비스지역 코드로 작성.
- "경상남도 하동군등" 같은 지역정보가 생성 되었지만 행정도 DB 정보 없이 틀린 정보로 판단 할 수 없다고 생각 하여 "등" 제거 하지 않음
- 서비스 지역 "화순" , 이후 "화순군" 은 "화순군" 으로 통합

###### 추가
- 연동 규격 예시에서 문자열 prefix된 PK로 작성되어야 한다고 판단하여 TableGenerator를 통해 prefix 문자열 ID 생성


---

### 과제 진행 정도 

* 기본 필수 문제 
* 기본 필수 제약사항

---

### 실행방법

```

$> gradle bootRun  
 -- (서버포트 10080 로 지정되어있습니다.)

OR

gradle build
java -jar ./build/libs/test-0.0.1-SNAPSHOT.jar --server.port=[PORT]
```

--- 

### API Document
###### Swagger Page

URL : [http://localhost:10080/swagger-ui.html](http://localhost:10080/swagger-ui.html)

. . . 

>[서비스 지역 API](http://localhost:10080/swagger-ui.html#/region-controller)

###### [생태 관광 정보 CSV 파일을 통한 DB저장](http://localhost:10080/swagger-ui.html#/region-controller/insertCSVProgramUsingPOST)

```
POST /regions/bulkCSV
BODY : file (formData name)
```

###### [서비스 지역 목록 조회](http://localhost:10080/swagger-ui.html#/region-controller/getListUsingGET)

```
GET /regions
RESPONSE :
[
  {
    "region": "reg0002",
    "regionName": "강원도 속초"
  }
]
```

###### [생태 관광 정보 조회](http://localhost:10080/swagger-ui.html#/region-controller/getProgramUsingGET)

```
GET /regions/{regionId}
EX>
GET /regions/reg0002
RESPONSE :
[
  {
    "program": "prg0001",
    "prgmName": "자연과 문화를 함께 즐기는 설악산 기행",
    "theme": "문화생태체험,자연생태체험,",
    "serviceRegion": "강원도 속초",
    "prgmInfo": "설악산 탐방안내소, 신흥사, 권금성, 비룡폭포",
    "prgmDescription": " 설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요? 설악산에 대해 정확히 알고, 배우고, 느낄 수 있는 당일형 생태관광입니다."
  }
]
```


>[지역 프로그램 API](http://localhost:10080/swagger-ui.html#/program-controller)

###### [생태 관광 정보 추가](http://localhost:10080/swagger-ui.html#/program-controller/insertProgramUsingPOST)

```
POST /programs
BODY:
{
  "prgmDescription": "string",
  "prgmInfo": "string",
  "prgmName": "string",
  "program": "string",
  "serviceRegion": "string",
  "theme": "string"
}
```

###### [생태 관광 정보 수정](http://localhost:10080/swagger-ui.html#/program-controller/updateProgramUsingPUT)

```
POST /programs/{programId}
BODY:
{
  "prgmDescription": "string",
  "prgmInfo": "string",
  "prgmName": "string",
  "program": "string",
  "serviceRegion": "string",
  "theme": "string"
}
```

###### [생태 관광 정보 삭제](http://localhost:10080/swagger-ui.html#/program-controller/deleteProgramUsingDELETE)

```
DELETE /programs/{programId}
```

>[검색 API](http://localhost:10080/swagger-ui.html#/search-controller)

###### [서비스 지역명 기준 지역 조회](http://localhost:10080/swagger-ui.html#/search-controller/searchRegionUsingPOST)

```
POST /search/region
BODY : 
{
  "region": "string"
}
```


###### [프로그램 소개 키워드 조회](http://localhost:10080/swagger-ui.html#/search-controller/searchKeywordUsingPOST)

```
POST /search/keyword
BODY : 
{
  "keyword": "string"
}
```

###### [프로그램 상세 키워드 노출 횟수 조회](http://localhost:10080/swagger-ui.html#/search-controller/searchKeywordCountUsingPOST)

```
POST /search/keywordCount
BODY : 
{
  "keyword": "string"
}
```




