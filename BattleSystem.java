package StarRail_김건규;

import java.util.*;

public class BattleSystem {
    private List<Character> playerTeam;
    private List<Character> enemyTeam;
    private int roundCount;
    private Scanner scanner;
    private int skillPoints; // 스킬 포인트
    private int maxSkillPoints;
    
    public BattleSystem() {
        playerTeam = new ArrayList<>();
        enemyTeam = new ArrayList<>();
        roundCount = 0;
        scanner = new Scanner(System.in);
        skillPoints = 3; // 시작 스킬 포인트
        maxSkillPoints = 5;
    }
    
    public void addPlayerCharacter(Character character) {
        playerTeam.add(character);
    }
    
    public void addEnemy(Character enemy) {
        enemyTeam.add(enemy);
    }
    
    private void displayBattleUI() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           🌟 BATTLE STATUS 🌟                                ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║  💠 스킬 포인트: %d / %d                                                       ║\n", skillPoints, maxSkillPoints);
        System.out.println("║                                                                               ║");
        System.out.println("║  [아군 팀] 🛡️                                                                 ║");
        System.out.println("║  ───────────────────────────────────────────────────────────────────────────  ║");
        
        for (int i = 0; i < playerTeam.size(); i++) {
            Character c = playerTeam.get(i);
            if (c.isAlive()) {
                System.out.print("║  [" + (i+1) + "] ");
                c.displayStatus();
            }
        }
        
        System.out.println("║                                                                               ║");
        System.out.println("║  [적 팀] ⚔️                                                                    ║");
        System.out.println("║  ───────────────────────────────────────────────────────────────────────────  ║");
        
        for (int i = 0; i < enemyTeam.size(); i++) {
            Character c = enemyTeam.get(i);
            if (c.isAlive()) {
                System.out.print("║  [" + (i+1) + "] ");
                c.displayStatus();
            }
        }
        
        System.out.println("║                                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
    }
    
    private void displayActionOrder() {
        List<Character> allCharacters = new ArrayList<>();
        allCharacters.addAll(playerTeam);
        allCharacters.addAll(enemyTeam);
        
        allCharacters.removeIf(c -> !c.isAlive());
        allCharacters.sort(Comparator.comparingInt(Character::getActionValue));
        
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│          📊 행동 순서 (Action Order)                   │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        
        for (int i = 0; i < Math.min(6, allCharacters.size()); i++) {
            Character c = allCharacters.get(i);
            String side = playerTeam.contains(c) ? "🛡️" : "⚔️";
            String energyStatus = c.canUseUltimate() ? "⚡준비완료" : String.format("(%d/%d)", c.getEnergy(), c.getMaxEnergy());
            System.out.printf("│  %d순위: %s %-10s │ AV: %-5d │ %s\n", 
                i+1, side, c.getName(), c.getActionValue(), energyStatus);
        }
        
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }
    
    public void startBattle() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                          🌟 전투 시작! 🌟");
        System.out.println("=".repeat(80));
        
        while (!isBattleOver()) {
            roundCount++;
            System.out.println("\n");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                            🔷 ROUND " + roundCount + " 🔷                                       ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
            
            displayBattleUI();
            displayActionOrder();
            
            Character actor = getNextActor();
            
            if (actor == null || !actor.isAlive()) {
                continue;
            }
            
            System.out.println("\n" + "─".repeat(80));
            System.out.println(">>> " + actor.getName() + "의 차례!");
            System.out.println("─".repeat(80));
            
            if (playerTeam.contains(actor)) {
                executePlayerTurn(actor);
            } else {
                executeEnemyTurn(actor);
            }
            
            actor.advanceAction();
            
            System.out.println("\n[Enter 키를 눌러 계속...]");
            scanner.nextLine();
        }
        
        displayBattleResult();
    }
    
    private Character getNextActor() {
        List<Character> allCharacters = new ArrayList<>();
        allCharacters.addAll(playerTeam);
        allCharacters.addAll(enemyTeam);
        
        allCharacters.removeIf(c -> !c.isAlive());
        
        if (allCharacters.isEmpty()) return null;
        
        allCharacters.sort(Comparator.comparingInt(Character::getActionValue));
        
        Character next = allCharacters.get(0);
        int minAV = next.getActionValue();
        
        for (Character c : allCharacters) {
            c.reduceActionValue(minAV);
        }
        
        return next;
    }
    
    private void executePlayerTurn(Character actor) {
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.println("│        행동을 선택하세요:            │");
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│  1️⃣  기본 공격 (SP +1)               │");
        System.out.printf("│  2️⃣  전투 스킬 (SP -1) [%s]         %s\n", 
            skillPoints > 0 ? "✅" : "❌", "│");
        
        if (actor.canUseUltimate()) {
            System.out.println("│  3️⃣  궁극기 ⚡ (사용 가능!)           │");
        } else {
            System.out.printf("│  3️⃣  궁극기 (에너지: %d/%d)         │\n", 
                actor.getEnergy(), actor.getMaxEnergy());
        }
        
        System.out.println("└──────────────────────────────────────┘");
        System.out.printf("현재 SP: %d / %d\n", skillPoints, maxSkillPoints);
        System.out.print("선택: ");
        
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            choice = 1;
        }
        
        if (choice == 3 && actor.canUseUltimate()) {
            Character[] targets = enemyTeam.toArray(new Character[0]);
            actor.ultimate(targets);
        } else if (choice == 2 && skillPoints > 0) {
            // 스킬 사용
            if (actor.getRole().equals("DPS")) {
                // 딜러는 적 선택
                System.out.println("\n공격할 대상을 선택하세요:");
                Character target = selectTarget(enemyTeam);
                if (target != null) {
                    actor.skill(target, playerTeam.toArray(new Character[0]));
                    skillPoints--;
                }
            } else if (actor.getRole().equals("HEALER")) {
                // 힐러는 아군 선택
                System.out.println("\n힐할 아군을 선택하세요:");
                Character target = selectTarget(playerTeam);
                if (target != null) {
                    actor.skill(target, playerTeam.toArray(new Character[0]));
                    skillPoints--;
                }
            } else if (actor.getRole().equals("BUFFER")) {
                // 버퍼는 아군 선택
                System.out.println("\n버프를 줄 아군을 선택하세요:");
                Character target = selectTarget(playerTeam);
                if (target != null) {
                    actor.skill(target, playerTeam.toArray(new Character[0]));
                    skillPoints--;
                }
            }
        } else {
            // 기본 공격
            System.out.println("\n공격할 대상을 선택하세요:");
            Character target = selectTarget(enemyTeam);
            if (target != null) {
                actor.basicAttack(target);
                skillPoints = Math.min(maxSkillPoints, skillPoints + 1);
            }
        }
    }
    
    private Character selectTarget(List<Character> team) {
        List<Character> aliveTargets = new ArrayList<>();
        for (int i = 0; i < team.size(); i++) {
            if (team.get(i).isAlive()) {
                aliveTargets.add(team.get(i));
                System.out.printf("%d. %s (HP: %d/%d)\n", 
                    i+1, team.get(i).getName(), 
                    team.get(i).getCurrentHp(), 
                    team.get(i).getMaxHp());
            }
        }
        
        System.out.print("대상 선택: ");
        int targetChoice = -1;
        try {
            targetChoice = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (Exception e) {
            targetChoice = 0;
        }
        
        if (targetChoice >= 0 && targetChoice < team.size() && team.get(targetChoice).isAlive()) {
            return team.get(targetChoice);
        } else if (!aliveTargets.isEmpty()) {
            return aliveTargets.get(0);
        }
        return null;
    }
    
    private void executeEnemyTurn(Character actor) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        if (actor.canUseUltimate() && Math.random() > 0.4) {
            Character[] targets = playerTeam.toArray(new Character[0]);
            actor.ultimate(targets);
        } else {
            Character target = selectAlivePlayer();
            if (target != null) {
                actor.basicAttack(target);
            }
        }
    }
    
    private Character selectAlivePlayer() {
        List<Character> alivePlayers = new ArrayList<>();
        for (Character p : playerTeam) {
            if (p.isAlive()) alivePlayers.add(p);
        }
        
        if (alivePlayers.isEmpty()) return null;
        return alivePlayers.get((int)(Math.random() * alivePlayers.size()));
    }
    
    private boolean isBattleOver() {
        boolean allPlayersDead = playerTeam.stream().noneMatch(Character::isAlive);
        boolean allEnemiesDead = enemyTeam.stream().noneMatch(Character::isAlive);
        
        return allPlayersDead || allEnemiesDead;
    }
    
    private void displayBattleResult() {
        System.out.println("\n\n");
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                ║");
        System.out.println("║                     ⚔️  전투 종료!  ⚔️                          ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        boolean playerWin = playerTeam.stream().anyMatch(Character::isAlive);
        
        System.out.println();
        if (playerWin) {
            System.out.println("        ✨ ════════════════════════════════ ✨");
            System.out.println("                  ★★★ 승리! ★★★");
            System.out.println("        ✨ ════════════════════════════════ ✨");
            System.out.println("\n           🎉 아군 팀이 승리했습니다! 🎉");
            
            // 도전과제 체크
            System.out.println("\n┌──────────────────────────────────────────────────┐");
            System.out.println("│              🏆 도전과제 달성 확인 🏆            │");
            System.out.println("├──────────────────────────────────────────────────┤");
            
            if (roundCount <= 3) {
                System.out.println("│  ⭐⭐⭐ 완벽한 승리!                            │");
                System.out.println("│  '3라운드 이내 승리' 도전과제 달성! ✅          │");
            } else if (roundCount <= 5) {
                System.out.println("│  ⭐⭐ 우수한 승리!                              │");
                System.out.println("│  '5라운드 이내 승리' 도전과제 달성! ✅          │");
            } else if (roundCount <= 8) {
                System.out.println("│  ⭐ 일반 승리                                   │");
                System.out.println("│  '8라운드 이내 승리' 도전과제 달성! ✅          │");
            } else {
                System.out.println("│  승리했지만 시간이 오래 걸렸습니다.             │");
                System.out.println("│  더 빠른 승리를 목표로 해보세요!               │");
            }
            
            // 생존자 체크
            int survivors = (int) playerTeam.stream().filter(Character::isAlive).count();
            if (survivors == 4) {
                System.out.println("│  💯 '아군 전원 생존' 도전과제 달성! ✅          │");
            }
            
            System.out.println("└──────────────────────────────────────────────────┘");
            
        } else {
            System.out.println("        ✖ ════════════════════════════════ ✖");
            System.out.println("                  ✖✖✖ 패배... ✖✖✖");
            System.out.println("        ✖ ════════════════════════════════ ✖");
            System.out.println("\n           💀 아군 팀이 전멸했습니다... 💀");
        }
        
        System.out.println("\n┌──────────────────────────────────────────────────┐");
        System.out.println("│                  📊 전투 통계                    │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.printf("│  총 라운드 수: %d                                \n", roundCount);
        
        System.out.println("│                                                  │");
        System.out.println("│  [생존 캐릭터]                                   │");
        
        boolean hasSurvivors = false;
        for (Character c : playerTeam) {
            if (c.isAlive()) {
                System.out.printf("│  💚 %-10s (HP: %4d/%4d)                  \n", 
                    c.getName(), c.getCurrentHp(), c.getMaxHp());
                hasSurvivors = true;
            }
        }
        
        if (!hasSurvivors) {
            System.out.println("│  없음                                            │");
        }
        
        System.out.println("└──────────────────────────────────────────────────┘");
    }
}