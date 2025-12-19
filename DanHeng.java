package StarRail_김건규;

public class DanHeng extends Character {
    
    public DanHeng() {
        super("단항", 800, 120, 60, 110, 120, "DPS");
    }
    
    @Override
    public int basicAttack(Character target) {
        System.out.println("\n🔵 " + name + "의 기본 공격!");
        int damage = attack;
        target.takeDamage(damage);
        gainEnergy(30);
        return damage;
    }
    
    @Override
    public void skill(Character target, Character[] allies) {
        System.out.println("\n⚔️ " + name + "의 전투 스킬: 구름 관통!");
        int damage = (int)(attack * 1.8);
        System.out.println("💥 강화된 창격으로 " + target.getName() + "을(를) 공격!");
        target.takeDamage(damage);
        gainEnergy(40);
    }
    
    @Override
    public void ultimate(Character[] targets) {
        if (!canUseUltimate()) return;
        
        System.out.println("\n✨ ═══════════════════════════════════════ ✨");
        System.out.println("   ★★★ " + name + "의 궁극기: 구름을 가르는 창! ★★★");
        System.out.println("✨ ═══════════════════════════════════════ ✨");
        
        Character target = null;
        int lowestHp = Integer.MAX_VALUE;
        
        for (Character t : targets) {
            if (t.isAlive() && t.getCurrentHp() < lowestHp) {
                lowestHp = t.getCurrentHp();
                target = t;
            }
        }
        
        if (target != null) {
            int damage = (int)(attack * 2.5);
            System.out.println("💥 강력한 창격으로 " + target.getName() + "을(를) 관통합니다!");
            target.takeDamage(damage);
        }
        
        consumeEnergy();
    }
}