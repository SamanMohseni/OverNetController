package Database;

public class Database {
    private static Database instance = null;

    private boolean upToDate;
    private boolean on;
    private boolean charge;
    private boolean chargeSent;

    private Database() {
        upToDate = false;
        on = false;
        charge = false;
        chargeSent = false;
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public boolean isUpToDate() {
        return upToDate;
    }

    public void setUpToDate(boolean upToDate) {
        this.upToDate = upToDate;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public String getState(){
        if(isOn()){
            return "on";
        }
        return "off";
    }

    public boolean isCharge() {
        return charge;
    }

    public String getCharge(){
        if(isCharge()){
            return "charge";
        }
        return "dis";
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public boolean isChargeSent() {
        return chargeSent;
    }

    public void setChargeSent(boolean chargeSent) {
        this.chargeSent = chargeSent;
    }
}
