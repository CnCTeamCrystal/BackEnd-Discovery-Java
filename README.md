# Backend-Discovery_Java_version

Crystal Eye의 Spring Boot 기반 Discovery Service

### 사용 프레임워크(런타임 및 버전 상세 필요)
* Spring-Boot 1.5.10.RELEASE
* Maven 4.0.0

### 프로젝트 구조
Spring Boot 기반의 프로젝트를 jar 파일로 패키징하여 PaaS상에 배포

### 사용 방법
해당 jar 파일이 있는 곳에서 
java -jar 파일명.jar

### 기능 (추 후 추가 가능)
> 검색(Discovery)
>> 실시간\
>> 특정일시\
>> 긍정 부정


# API
## Real Time
### URI
HTTP|URI
---|---
GET|/user/add/

### Parameter
Parameter|Parameter명|Data Type
---|---|---
company|company name|String

### Response
{
	..
	"results" : [
		{
			..
			"url" : 
			"highlight" : {
				..
				"enriched_text.keywords.text" :
				..
			}
			..
		}
		..
}
				