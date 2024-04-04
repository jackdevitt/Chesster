public class Message {
   
   private boolean successful;
   private String message; 
   
   public Message(boolean successful, String message) {
      this.successful = successful;
      this.message = message;
   }
   
   public Message(boolean successful) {
      this.successful = successful;
   }
   
   public boolean isError() {
      return successful;
   }
   
   public String getMessage() {
      return message;
   }
}