create table TRANSFERS (
    ID  INT AUTO_INCREMENT PRIMARY KEY,
    SOURCE_ID INT NOT NULL,
    TARGET_ID INT NOT NULL,
    AMOUNT DECIMAL(19,2),
    TRANSFER_TIME DATE,
    FOREIGN KEY (SOURCE_ID) REFERENCES ACCOUNTS (ID) ON UPDATE RESTRICT ON DELETE CASCADE,
    FOREIGN KEY (TARGET_ID) REFERENCES ACCOUNTS (ID) ON UPDATE RESTRICT ON DELETE CASCADE
)
