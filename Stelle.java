package StarRail_김건규;

public class Stelle extends Character {
    
    public Stelle() {
        super("스텔레", 850, 110, 70, 100, 120, "DPS");
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
        System.out.println("\n⚔️ " + name + "의 전투 스킬: 개척의 일격!");
        int damage = (int)(attack * 1.6);
        System.out.println("💥 강화된 공격으로 " + target.getName() + "을(를) 타격!");
        target.takeDamage(damage);
        gainEnergy(40);
    }
    
    @Override
    public void ultimate(Character[] targets) {
        if (!canUseUltimate()) return;
        
        System.out.println("\n✨ ═══════════════════════════════════════ ✨");
        System.out.println("   ★★★ " + name + "의 궁극기: 개척의 의지! ★★★");
        System.out.println("✨ ═══════════════════════════════════════ ✨");
        
        System.out.println("💥 모든 적에게 강력한 광역 공격!");
        for (Character target : targets) {
            if (target.isAlive()) {
                int damage = (int)(attack * 1.5);
                target.takeDamage(damage);
            }
        }
        
        consumeEnergy();
    }
}