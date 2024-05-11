-- Create PACKAGE table
CREATE TABLE PACKAGE (
    PACKAGE_ID INTEGER PRIMARY KEY,
    PACKAGE_NAME VARCHAR(200),
    PRICE DECIMAL,
    AMOUNT_MINUTES INTEGER,
    AMOUNT_DATA INTEGER,
    AMOUNT_SMS INTEGER,
    PERIOD INTEGER
);

-- Create CUSTOMER table
CREATE TABLE CUSTOMER (
    CUST_ID INTEGER PRIMARY KEY,
    MSISDN VARCHAR(15),
    NAME VARCHAR(100),
    SURNAME VARCHAR(100),
    EMAIL VARCHAR(100),
    PASSWORD VARCHAR(20),
    SDATE TIMESTAMP,
    STATUS VARCHAR(2),
    SECURITY_KEY VARCHAR(200),
    CONSTRAINT CUSTOMER_MSISDN_UN UNIQUE (MSISDN)
);

-- Create BALANCE table
CREATE TABLE BALANCE (
    BALANCE_ID INTEGER PRIMARY KEY,
    PACKAGE_ID INTEGER,
    CUST_ID INTEGER,
    PARTITION_ID INTEGER,
    BAL_LVL_MINUTES INTEGER,
    BAL_LVL_SMS INTEGER,
    BAL_LVL_DATA INTEGER,
    SDATE TIMESTAMP,
    EDATE TIMESTAMP,
    PRICE DECIMAL,
    BAL_LVL_MONEY DECIMAL,
    CONSTRAINT CUSTID_FK FOREIGN KEY (CUST_ID) REFERENCES CUSTOMER (CUST_ID),
    CONSTRAINT PACKAGEID_FK FOREIGN KEY (PACKAGE_ID) REFERENCES PACKAGE (PACKAGE_ID)
);