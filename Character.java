package StarRail_김건규;

public abstract class Character {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected int energy;
    protected int maxEnergy;
    protected boolean isAlive;
    protected int actionValue; // 행동 게이지
    protected String role; // 역할: DPS, HEALER, BUFFER
    
    public Character(String name, int maxHp, int attack, int defense, int speed, int maxEnergy, String role) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.energy = 0;
        this.maxEnergy = maxEnergy;
        this.isAlive = true;
        this.actionValue = 10000 / speed; // 속도 기반 초기 행동값
        this.role = role;
    }
    
    public abstract int basicAttack(Character target);
    public abstract void skill(Character target, Character[] allies); // 스킬 추가
    public abstract void ultimate(Character[] targets);
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense / 2);
        currentHp -= actualDamage;
        
        if (currentHp <= 0) {
            currentHp = 0;
            isAlive = false;
            System.out.println("💀 " + name + "이(가) 쓰러졌습니다!");
        }
        
        System.out.println("🗡️  " + name + "이(가) " + actualDamage + " 데미지! (HP: " + currentHp + "/" + maxHp + ")");
    }
    
    public void gainEnergy(int amount) {
        energy = Math.min(maxEnergy, energy + amount);
        if (energy >= maxEnergy) {
            System.out.println("⚡ " + name + "의 궁극기 준비 완료!");
        }
    }
    
    public boolean canUseUltimate() {
        return energy >= maxEnergy;
    }
    
    protected void consumeEnergy() {
        energy = 0;
    }
    
    public void advanceAction() {
        actionValue = 10000 / speed;
    }
    
    public void reduceActionValue(int amount) {
        actionValue -= amount;
    }
    
    // Getter/Setter
    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public int getSpeed() { return speed; }
    public boolean isAlive() { return isAlive; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getActionValue() { return actionValue; }
    public void setActionValue(int value) { this.actionValue = value; }
    public String getRole() { return role; }
    
    public void displayStatus() {
        String hpBar = getHpBar();
        String energyBar = getEnergyBar();
        System.out.printf("%-10s │ HP: %-20s│ Energy: %-15s│ SPD: %-3d │ AV: %d\n", 
            name, hpBar, energyBar, speed, actionValue);
    }
    
    private String getHpBar() {
        int barLength = 10;
        int filled = (int)((double)currentHp / maxHp * barLength);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) {
            if (i < filled) bar.append("█");
            else bar.append("░");
        }
        return bar.toString() + String.format(" %d/%d", currentHp, maxHp);
    }
    
    private String getEnergyBar() {
        int barLength = 5;
        int filled = (int)((double)energy / maxEnergy * barLength);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) {
            if (i < filled) bar.append("⚡");
            else bar.append("○");
        }
        return bar.toString() + String.format(" %d/%d", energy, maxEnergy);
    }
}