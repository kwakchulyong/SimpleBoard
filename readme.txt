인터넷에 있는 예제 소스 참조해서 만들었습니다.


개발환경

Programming Language - Java 1.7
IDE - Eclipse
DB - Mysql 
Framework - MyBatis, Spring 4
Build Tool - Maven

설치

테이블 생성
 
    CREATE TABLE TBL_BOARD (
      BGNO INT(11),								-- 게시판 그룹번호
      BRDNO int(11) NOT NULL AUTO_INCREMENT,	-- 글 번호
      BRDTITLE varchar(255),						-- 글 제목
      BRDWRITER varchar(20),						-- 작성자
      BRDMEMO   varchar(4000),					-- 글 내용
      BRDDATE	datetime,							-- 작성일자
      BRDHIT INT,									-- 조회수
      BRDDELETEFLAG CHAR(1),						-- 삭제 여부
      PRIMARY KEY (BRDNO)
    ) ;

	CREATE TABLE TBL_BOARDFILE (
	    FILENO INT(11)  NOT NULL AUTO_INCREMENT, -- 파일 번호
	    BRDNO INT(11),                           -- 글번호
	    FILENAME VARCHAR(100),                   -- 파일명
	    REALNAME VARCHAR(30),                    -- 실제파일명
	    FILESIZE INT,                            -- 파일 크기
	    PRIMARY KEY (FILENO)
	);
    
	CREATE TABLE TBL_BOARDREPLY (
	    BRDNO INT(11) NOT NULL,					-- 게시물 번호
	    RENO INT(11) NOT NULL,                 -- 댓글 번호
	    REWRITER VARCHAR(10) NOT NULL,         -- 작성자
	    REMEMO VARCHAR(500) DEFAULT NULL,      -- 댓글내용
	    REDATE DATETIME DEFAULT NULL,          -- 작성일자
	    REDELETEFLAG VARCHAR(1) NOT NULL,      -- 삭제여부
	    REPARENT INT(11),							-- 부모 댓글	
	    REDEPTH INT,								-- 깊이	
	    REORDER INT,								-- 순서
	    PRIMARY KEY (RENO)
	);

	CREATE TABLE TBL_BOARDGROUP (
	  BGNO INT(11) NOT NULL AUTO_INCREMENT,		-- 게시판 그룹번호
	  BGNAME VARCHAR(50),							-- 게시판 그룹명
	  BGPARENT INT(11),							-- 게시판 그룹 부모
	  BGDELETEFLAG CHAR(1),						-- 삭제 여부
	  BGUSED CHAR(1),								-- 사용 여부
	  BGREPLY CHAR(1),							-- 댓글 사용여부
	  BGREADONLY CHAR(1),							-- 글쓰기 가능 여부
	  BGDATE DATETIME,							-- 생성일자
	  PRIMARY KEY (BGNO)
	);


DB정보 입력
\src\main\webapp\WEB-INF 폴더에 있는 applicationContext.xml 수정


