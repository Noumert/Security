import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Account
{
    public String id;
    public int money;
    public ZonedDateTime deletionTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ZonedDateTime getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(ZonedDateTime deletionTime) {
        this.deletionTime = deletionTime;
    }
}
