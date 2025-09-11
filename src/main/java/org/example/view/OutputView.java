package org.example.view;

public class OutputView {

    public static void printProgramMenu() {
        System.out.println("==============================");
        System.out.println("1. 고객 리스트 확인 ");
        System.out.println("2. 고객 추가");
        System.out.println("3. 로그인 할 고객 선택");
        System.out.println("4. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    public static void printUserServiceMenu(String name) {
        System.out.println("==============================");
        System.out.println(name +"님 안녕하세요.");
        System.out.println("1. 새 계좌 개설 ");
        System.out.println("2. 계좌 리스트 확인");
        System.out.println("3. 이용할 계좌 선택");
        System.out.println("4. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    public static void printBankAccountServiceMenu(String bankAccount) {
        System.out.println("==============================");
        System.out.println(bankAccount+ "계좌 서비스입니다. ");
        System.out.println("1. 입금");
        System.out.println("2. 출금");
        System.out.println("3. 이체");
        System.out.println("4. 결제");
        System.out.println("5. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }
}
