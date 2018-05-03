USE heroku_eea3932bd3d56f0;

SET FOREIGN_KEY_CHECKS = 0;
SET @@AUTO_INCREMENT_INCREMENT=1;

DROP TABLE IF EXISTS PLAYERS;

CREATE TABLE PLAYERS (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  LOGIN VARCHAR(128) NOT NULL,
  PASSWORD VARCHAR(128) DEFAULT NULL,
  EMAIL VARCHAR(128) DEFAULT NULL,

  PRIMARY KEY (ID),
  CONSTRAINT UC_PLAYER UNIQUE (LOGIN)
)
ENGINE = InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET = UTF8;

INSERT INTO PLAYERS (ID, LOGIN, PASSWORD, EMAIL) VALUES (1, 'ANONYMOUS', NULL, NULL);

DROP TABLE IF EXISTS GAMES;

CREATE TABLE GAMES (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  PLAYER_BLACK INTEGER NOT NULL DEFAULT 1,
  PLAYER_WHITE INTEGER NOT NULL DEFAULT 1,
  STATE VARCHAR(1024) DEFAULT NULL,
  START_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UPDATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (ID),

  CONSTRAINT FK_PLAYER_BK_IDX FOREIGN KEY (PLAYER_BLACK) 
  REFERENCES PLAYERS (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT FK_PLAYER_WH_IDX FOREIGN KEY (PLAYER_WHITE) 
  REFERENCES PLAYERS (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) 
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS ARCHIVE;

CREATE TABLE ARCHIVE (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  WINNER INTEGER NOT NULL DEFAULT 1,
  LOSER INTEGER NOT NULL DEFAULT 1,
  SAVED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (ID),

  CONSTRAINT FK_WINNER_IDX FOREIGN KEY (WINNER) 
  REFERENCES PLAYERS (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT FK_LOSER_IDX FOREIGN KEY (LOSER) 
  REFERENCES PLAYERS (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) 
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

DROP PROCEDURE IF EXISTS ARCHIVISE;

DELIMITER //
CREATE PROCEDURE ARCHIVISE (IN GAME_ID INTEGER, IN WINNER_COLOR VARCHAR(1))
BEGIN
  CASE UPPER(WINNER_COLOR)
    WHEN 'B' THEN 
      BEGIN
        INSERT INTO ARCHIVE (WINNER, LOSER) 
          SELECT PLAYER_BLACK, PLAYER_WHITE FROM GAMES WHERE ID = GAME_ID LIMIT 1;
		DELETE FROM GAMES WHERE ID = GAME_ID;
	  END;
    WHEN 'W' THEN 
      BEGIN
        INSERT INTO ARCHIVE (WINNER, LOSER) 
          SELECT PLAYER_WHITE, PLAYER_BLACK FROM GAMES WHERE ID = GAME_ID LIMIT 1;
	    DELETE FROM GAMES WHERE ID = GAME_ID;
	  END;
    ELSE
      BEGIN
	  END;
  END CASE;
END //
DELIMITER ;

SET FOREIGN_KEY_CHECKS = 1;