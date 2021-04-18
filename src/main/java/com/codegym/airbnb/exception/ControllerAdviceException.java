package com.codegym.airbnb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ControllerAdviceException {

  @ResponseBody
  @ExceptionHandler(BookingNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String BookingNotFoundHandler(BookingNotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String UserNotFoundHandler(UserNotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(RoomNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String RoomNotFoundHandler(RoomNotFoundException ex) {
    return ex.getMessage();
  }

}
