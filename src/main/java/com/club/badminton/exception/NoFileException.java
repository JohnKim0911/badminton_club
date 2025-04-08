package com.club.badminton.exception;

public class NoFileException extends RuntimeException {

    public NoFileException() {
        super("첨부파일이 없습니다.");
    }
}
