# sideProject-mchen81

## Pre-process
* Install tesseract engin.  
Please refer to https://github.com/tesseract-ocr/tesseract/wiki
* On the resources/SQL, there are some DDL/Procedures scripts, please run them on your database.
* On the resources/applicationcontext/Datasource.xml, you have to set your own database jdbc to connect to your DB.

## How to use the web.
* When server is started, it will load the shopping history and purchased items from database, and put them into maps.
* You have to click 'submit' button to update the data in DB, or it will just change the temporary data.  

## Upload receipts to track expense
* You can upload an image of receipt, it must be as clear as possible
* After you upload an image, you can check if there are any mistakes and edit them.
