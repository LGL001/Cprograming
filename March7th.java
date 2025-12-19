package StarRail_김건규;

public class March7th extends Character {
    
    public March7th() {
        super("March7th", 900, 90, 80, 95, 110, "HEALER");
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
        System.out.println("\n💚 " + name + "의 전투 스킬: 소녀의 수호!");
        int healAmount = 180;
        int newHp = Math.min(target.getMaxHp(), target.getCurrentHp() + healAmount);
        target.currentHp = newHp;
        System.out.println("💚 " + target.getName() + "의 HP가 " + healAmount + " 회복! (HP: " + newHp + "/" + target.getMaxHp() + ")");
        gainEnergy(35);
    }
    
    @Override
    public void ultimate(Character[] targets) {
        if (!canUseUltimate()) return;
        
        System.out.println("\n✨ ═══════════════════════════════════════ ✨");
        System.out.println("   ★★★ " + name + "의 궁극기: 얼음 소녀의 매혹! ★★★");
        System.out.println("✨ ═══════════════════════════════════════ ✨");
        
        System.out.println("❄️  아군 전체에게 얼음 보호막을 생성합니다!");
        for (Character ally : targets) {
            if (ally.isAlive() && ally.getCurrentHp() < ally.getMaxHp()) {
                int healAmount = 150;
                int newHp = Math.min(ally.getMaxHp(), ally.getCurrentHp() + healAmount);
                ally.currentHp = newHp;
                System.out.println("💚 " + ally.getName() + "의 HP가 " + healAmount + " 회복! (HP: " + newHp + "/" + ally.getMaxHp() + ")");
            }
        }
        
        consumeEnergy();
    }
}