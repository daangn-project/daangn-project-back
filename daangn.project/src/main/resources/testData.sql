
INSERT INTO MEMBER (USERNAME, PASSWORD, NICKNAME, EMAIL) VALUES ('skc1', '1234','skc1' , 'skc@naver.com');

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO MEMBER_AUTHORITY (MEMBER_ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO MEMBER_AUTHORITY (MEMBER_ID, AUTHORITY_NAME) values (2, 'ROLE_ADMIN');