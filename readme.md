<h1>Development environment</h1>
<p>

Programming Language - Java 1.7</br>
IDE - Eclipse<br/>
DB - Mysql <br/>
Framework - MyBatis, Spring 4<br/>
Build Tool - Maven<br/>
Traditional Korean style Spring Framework board

modify
\src\main\webapp\WEB-INF\applicationContext.xml
</p>
<h1>2018.04.30 relesed</h1>
<center>
<img src="https://github.com/kwakchulyong/SimpleBoard/blob/master/screenshot/20180430/boardlist.png" width="300" height="300">
</center>

	
	



<h1>mysql query</h1>
 <p>
 
    CREATE TABLE TBL_BOARD (
      BGNO INT(11),								
      BRDNO int(11) NOT NULL AUTO_INCREMENT,	
      BRDTITLE varchar(255),					
      BRDWRITER varchar(20),					
      BRDMEMO   varchar(4000),					
      BRDDATE	datetime,						
      BRDHIT INT,								
      BRDDELETEFLAG CHAR(1),					
      PRIMARY KEY (BRDNO)
    ) ;

	CREATE TABLE TBL_BOARDFILE (
	    FILENO INT(11)  NOT NULL AUTO_INCREMENT,
	    BRDNO INT(11),                          
	    FILENAME VARCHAR(100),                  
	    REALNAME VARCHAR(30),                   
	    FILESIZE INT,                           
	    PRIMARY KEY (FILENO)
	);
    
	CREATE TABLE TBL_BOARDREPLY (
	    BRDNO INT(11) NOT NULL,				
	    RENO INT(11) NOT NULL,                
	    REWRITER VARCHAR(10) NOT NULL,        
	    REMEMO VARCHAR(500) DEFAULT NULL,     
	    REDATE DATETIME DEFAULT NULL,         
	    REDELETEFLAG VARCHAR(1) NOT NULL,     
	    REPARENT INT(11),					
	    REDEPTH INT,						
	    REORDER INT,						
	    PRIMARY KEY (RENO)
	);

	CREATE TABLE TBL_BOARDGROUP (
	  BGNO INT(11) NOT NULL AUTO_INCREMENT,		
	  BGNAME VARCHAR(50),						
	  BGPARENT INT(11),							
	  BGDELETEFLAG CHAR(1),						
	  BGUSED CHAR(1),							
	  BGREPLY CHAR(1),							
	  BGREADONLY CHAR(1),						
	  BGDATE DATETIME,							
	  PRIMARY KEY (BGNO)
	);
	
	//addition table
	create table user (
    userid varchar(100),
    password varchar(100),
    username varchar(100),
    email varchar(100)
	);

	insert into user values ('test','1234','테스트','test@test.com');
	
	
</p>


