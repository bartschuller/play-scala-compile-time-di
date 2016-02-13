package service

trait MyService {
  def secretWord: String
}

case class MyDefaultService(secretWord: String) extends MyService