package pe.edu.cibertec.exament1.loyality;

public interface LoyaltyRepository {
    boolean isVipUser(Long userId);
    void addPoints(Long userId, int points);

}
