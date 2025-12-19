package StarRail_김건규;

public class Bronya extends Character {
    
    public Bronya() {
        super("브로냐", 950, 100, 75, 105, 110, "BUFFER");
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
        System.out.println("\n✨ " + name + "의 전투 스킬: 내가돕게해줘");
        System.out.println("⚡ " + target.getName() + "에게 버프와 턴 당김!");
        
        // 대상의 행동값을 50% 감소시켜 더 빨리 행동하게 함
        int currentAV = target.getActionValue();
        target.setActionValue((int)(currentAV * 0.5));
        
        System.out.println("🔥 " + target.getName() + "의 행동 순서가 앞당겨집니다!");
        System.out.println("💪 공격력이 일시적으로 상승합니다! (다음 공격 강화)");
        
        target.gainEnergy(30);
        gainEnergy(35);
    }
    
    @Override
    public void ultimate(Character[] targets) {
        if (!canUseUltimate()) return;
        
        System.out.println("\n✨ ═══════════════════════════════════════ ✨");
        System.out.println("   ★★★ " + name + "의 궁극기: 전진! ★★★");
        System.out.println("✨ ═══════════════════════════════════════ ✨");
        
        System.out.println("✨ 아군 전체에게 강력한 버프 부여!");
        
        Character bestAttacker = null;
        int highestAttack = 0;
        
        for (Character ally : targets) {
            if (ally.isAlive() && ally.attack > highestAttack && ally != this) {
                highestAttack = ally.attack;
                bestAttacker = ally;
            }
        }
        
        if (bestAttacker != null) {
            System.out.println("⚡ " + bestAttacker.getName() + "에게 집중 버프! 추가 에너지 충전!");
            bestAttacker.gainEnergy(50);
        }
        
        for (Character ally : targets) {
            if (ally.isAlive()) {
                int healAmount = 100;
                int newHp = Math.min(ally.getMaxHp(), ally.getCurrentHp() + healAmount);
                ally.currentHp = newHp;
                System.out.println("💚 " + ally.getName() + "의 HP가 " + healAmount + " 회복!");
            }
        }
        
        consumeEnergy();
    }
}