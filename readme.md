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
	
</p>


