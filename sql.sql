CREATE TABLE PRODUCT (
	BARCODE VARCHAR(30),
	P_NAME VARCHAR(60),
   ST_ VARCHAR(60),
   PRIMARY KEY(BARCODE)
);

INSERT INTO `product` VALUES ('8801056839017','게토레이레몬(1.5L)','st_냉장')

CREATE TABLE FOOD (
	F_NAME VARCHAR(20),
	THING1 VARCHAR(20),
	THING2 VARCHAR(20),
	THING3 VARCHAR(20),
	PRIMARY KEY(F_NAME)
);

INSERT INTO FOOD VALUES ('김치찌개', '김치', '돼지고기','대파');
INSERT INTO FOOD VALUES ('김치볶음밥', '김치', '양파', '햄');
INSERT INTO FOOD VALUES ('오이무침', '오이', '고추', '양파');
INSERT INTO FOOD VALUES ('두루치기', '돼지고기', '양파', '대파');
INSERT INTO FOOD VALUES ('떡볶이', '떡', '고추장', '대파');
INSERT INTO FOOD VALUES ('닭볶음탕', '닭고기', '감자', '양파');
INSERT INTO FOOD VALUES ('두부조림', '두부', '양파', '대파');
INSERT INTO FOOD VALUES ('미역국', '소고기', '미역', '마늘');
INSERT INTO FOOD VALUES ('계란장조림', '계란', '마늘', '간장');
INSERT INTO FOOD VALUES ('새우볶음밥', '새우', '당근', '양파');

CREATE TABLE USERS (
	U_ID VARCHAR(30),
	U_PW VARCHAR(60),
   U_NAME VARCHAR(60),
   EMAIL VARCHAR(60),
   PRIMARY KEY(U_ID)
);

INSERT INTO USERS VALUES ('dhqudwn0', '1234', '오병주', '오병주');