package com.club.badminton.exception.attachment;

public class NoFileException extends RuntimeException {

    public NoFileException() {
        super("첨부파일이 없습니다.");
    }
}
