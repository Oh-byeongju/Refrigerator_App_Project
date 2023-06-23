# 📱 냉장고 관리 앱 프로젝트

## **📝 프로젝트 개요**
#### `1. 프로젝트 소개`
- Android-studio와 Python 언어를 이용하여 개발한 효율적인 냉장고 관리를 위한 앱입니다.
- 식자재들의 올바른 보관 방법 및 보관 기간을 앱을 통해 알려줘서 냉장고 관리의 효율성을 높이고 식자재가 낭비되는 것을 줄이기 위해 개발하였습니다.

#### `2. 개발 기간`
- 2022.03.15 ~ 2022.06.10

## **🛠 사용 기술**
#### `APP`
- JAVA
- Android-studio
- SQLite

#### `SERVER`
- Python
- MySQL
- TCP/IP 소켓통신

#### `API`
- 바코드 스캐너 API
- 카카오 OCR API
- 네이버 검색 API

## **💻 시스템 구성도**
<img width="100%" alt="Sys" src="https://user-images.githubusercontent.com/96694919/248229210-a2218cb3-ddc5-4e0d-bcd9-f57225747211.png"/>

#### 💡 프로젝트의 전체적인 시스템 구성도입니다.
1. 사용자가 애플리케이션에 접근하여 데이터를 입력하면 TCP/IP 소켓 통신을 이용해 서버로 해당 데이터를 전송합니다.
   
2. 서버에서는 데이터베이스에 접근하여 사용자의 입력에 맞는 데이터를 가져온 뒤 연산, 처리 과정을 거치고 나온 결과를 다시 애플리케이션에 전송합니다.
   
3. 애플리케이션은 전달받은 데이터를 화면에 출력함으로써 사용자에게 정보를 전달합니다.

## 🔎 주요 기능 소개
- **제품 등록**
	- 애플리케이션 사용자가 바코드 인식 또는 정보를 직접 입력하여 관리하고자 하는 제품을 등록하는 기능입니다.
	- 바코드 등록인 경우 바코드 스캐너 라이브러리를 이용하여 바코드를 인식하고, 유통기한의 사진을 촬영한 뒤 서버로 전달합니다. 서버에서는 바코드 정보를 이용해 DB 데이터를 검색하고, OCR을 이용하여 유통기한을 추출합니다. 이후 데이터를 사용자의 애플리케이션으로 전달하여 제품 등록의 편의성을 제공합니다.
	- 직접 등록인 경우 사용자가 제품에 대한 이름, 수량, 보관 장소, 유통기한, 등록일을 직접 입력한 후 제품 등록을 진행합니다.

- **제품 수정**
	- 제품의 이름, 수량, 보관 장소, 유통기한, 등록일을 수정하는 기능입니다. 
	- 저장되어 있는 제품의 정보를 가져와서 사용자에게 보여주고 수정 버튼을 누를 시 수정된 정보를 다시 DB에 저장합니다.

- **제품 삭제**
	- 등록된 제품을 삭제하는 기능입니다. 
	- 제품 항목을 길게 누르면 X 버튼이 활성화가 되고 해당 버튼을 누를 시 DB와 UI에서 해당 제품의 정보가 삭제됩니다.

- **유통기한 표시**
	- 제품 등록 시 입력된 유통기한과 현재 날짜를 이용하여 남은 유통기한을 구한 뒤 사용자에게 알려주는 기능입니다.
	- ‘D- '로 남은 일자가 표시되고 유통기한이 지났을 경우 '+'로 표시되어 해당 제품의 유통기한이 며칠이 지났는지 알 수 있습니다.

- **레시피 검색**
	- 사용자가 원하는 음식의 레시피를 검색하는 기능입니다.
	- 음식 이름을 입력 후 검색 버튼을 누르면 음식과 관련된 레시피 정보들이 담긴 블로그 리스트가 출력됩니다.

- **레시피 추천**
	- 냉장고 속에 있는 재료들로 만들 수 있는 음식의 레시피를 추천하는 기능입니다.
	- 일치 알고리즘을 통하여 DB에서 냉장고 안에 있는 재료들을 검색하고 조건이 일치하는 음식들을 리스트 형태로 사용자에게 보여줍니다.

- **유통기한 알림**
	- 유통기한의 남은 일자가 3일 이하의 제품이 있을 경우 사용자에게 ‘제품의 유통기한이 얼마남지 않았습니다.’ 라는 알림을 보내는 기능입니다.

## 📹 시연 영상

### 📌 제품 등록, 수정, 삭제 기능
<img width="100%" alt="gif" src="https://user-images.githubusercontent.com/96694919/248254877-13e6cf85-add1-4067-a59c-cd86692fc1f3.gif"/>

<br/>
### 📌 레시피 검색, 추천 및 유통기한 알림 기능
<img width="100%" alt="gif" src="https://user-images.githubusercontent.com/96694919/248254899-43147044-d476-41eb-a3ee-67d14108c9f7.gif"/>

## 👩‍💻 개선사항

### * 해당 프로젝트의 개선사항 및 보완할 점입니다.

- 데이터 통신을 할때 소켓을 이용한 통신이 아닌 HTTP 통신을 이용하여 실제 배포와 같은 환경을 구성(통신 환경 보완)
- Django와 같은 Python 프레임워크를 사용하여 서버 코드 리팩토링
- 등록된 제품 삭제 방식이 좀 더 유연해질 필요가 있음(전체 삭제 기능 등)
- 레시피를 추천해주는 알고리즘 개선 필요
- 유통기한 검출 정확도 향상을 위한 이미지 전처리 및 알고리즘 개선 필요
- 유통기한 표시가 없는 제품의 예상 유통기한 알림 기능 구현