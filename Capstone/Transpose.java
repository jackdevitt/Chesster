public class Transpose {
   
   private long hash;
   private int alpha;
   private int beta;
   private int score;
   private int depth;
   
   public Transpose(long hash, int alpha, int beta, int score, int depth) {
      this.hash = hash;
      this.alpha = alpha;
      this.beta = beta;
      this.score = score;
      this.depth = depth;
   }
   
   public long getHash() {
      return hash;
   }
   
   public int getAlpha() {
      return alpha;
   }
   
   public int getBeta() {
      return beta;
   }
   
   public int getScore() {
      return score;
   }
   
   public int getDepth() {
      return depth;
   }
   
   public boolean compareDepth(int depth) {
      return this.depth >= depth;
   }
}