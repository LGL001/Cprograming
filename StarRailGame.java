package StarRail_김건규;

public class StarRailGame {
    public static void main(String[] args) {
        System.out.println("\n\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║        🌟  붕괴: 스타레일 전투 시뮬레이터  🌟                 ║");
        System.out.println("║                                                               ║");
        System.out.println("║              Turn-Based Combat System                         ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n🎯 도전과제:");
        System.out.println("  ⭐⭐⭐ 3라운드 이내에 승리하기");
        System.out.println("  ⭐⭐   5라운드 이내에 승리하기");
        System.out.println("  ⭐     8라운드 이내에 승리하기");
        System.out.println("  💯     아군 전원 생존하기");
        
        System.out.println("\n💠 스킬 포인트 시스템:");
        System.out.println("  • 시작 SP: 3개 (최대 5개)");
        System.out.println("  • 기본 공격 → SP +1");
        System.out.println("  • 전투 스킬 → SP -1");
        System.out.println("  • 궁극기 → SP 소모 없음 (에너지 필요)");
        
        BattleSystem battle = new BattleSystem();
        
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🛡️  아군 팀 구성 중...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        battle.addPlayerCharacter(new Stelle());
        System.out.println("  ✅ 스텔레 - 개척자 (광역 딜러)");
        System.out.println("     스킬: 개척의 일격 - 강화 공격");
        
        battle.addPlayerCharacter(new DanHeng());
        System.out.println("  ✅ 단항 - 용의 계승자 (단일 딜러)");
        System.out.println("     스킬: 구름 관통 - 강력한 단일 공격");
        
        battle.addPlayerCharacter(new March7th());
        System.out.println("  ✅ 삼월칠일 - 얼음 소녀 (힐러)");
        System.out.println("     스킬: 소녀의 수호 - 단일 대상 힐");
        
        battle.addPlayerCharacter(new Bronya());
        System.out.println("  ✅ 브로냐 - 은빛 늑대 (버퍼/힐러)");
        System.out.println("     스킬: 전장의 지휘 - 아군 턴 당김 + 버프");
        
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("⚔️  적 팀 출현!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        battle.addEnemy(new Enemy("반물질 군단 병사", "엘리트", 600, 100, 50, 90));
        System.out.println("  ❌ 반물질 군단 병사 (엘리트)");
        
        battle.addEnemy(new Enemy("허수의 그림자", "일반", 500, 80, 40, 85));
        System.out.println("  ❌ 허수의 그림자 (일반)");
        
        battle.addEnemy(new Enemy("부패한 기사", "강력", 700, 110, 60, 95));
        System.out.println("  ❌ 부패한 기사 (강력)");
        
        System.out.println("\n" + "=".repeat(65));
        System.out.println("\n[Enter 키를 눌러 전투 시작...]");
        
        try {
            System.in.read();
        } catch (Exception e) {}
        
        battle.startBattle();
        
        System.out.println("\n\n게임을 종료합니다. 감사합니다! 👋");
    }
}