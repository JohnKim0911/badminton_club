package com.club.badminton.exception.validation.club;

public class DuplicatedClubNameException extends RuntimeException {

  public DuplicatedClubNameException() {
    super("이미 사용중인 클럽명입니다.");
  }
}
