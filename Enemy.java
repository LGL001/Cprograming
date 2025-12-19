package StarRail_김건규;

public class Enemy extends Character {
    private String enemyType;
    
    public Enemy(String name, String type, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed, 100, "ENEMY");
        this.enemyType = type;
    }
    
    @Override
    public int basicAttack(Character target) {
        System.out.println("\n🔴 " + name + "(" + enemyType + ")의 공격!");
        int damage = attack;
        target.takeDamage(damage);
        gainEnergy(40);
        return damage;
    }
    
    @Override
    public void skill(Character target, Character[] allies) {
        // 적은 스킬 사용 안함
    }
    
    @Override
    public void ultimate(Character[] targets) {
        if (!canUseUltimate()) return;
        
        System.out.println("\n💀 ═══════════════════════════════════════ 💀");
        System.out.println("   ★★★ " + name + "의 강력한 공격! ★★★");
        System.out.println("💀 ═══════════════════════════════════════ 💀");
        
        Character target = null;
        for (Character t : targets) {
            if (t.isAlive()) {
                target = t;
                break;
            }
        }
        
        if (target != null) {
            int damage = (int)(attack * 2.0);
            System.out.println("💥 " + target.getName() + "에게 강력한 일격!");
            target.takeDamage(damage);
        }
        
        consumeEnergy();
    }
    
    public String getEnemyType() {
        return enemyType;
    }
}