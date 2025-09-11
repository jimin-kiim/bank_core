package org.example.controller;

import org.example.constants.Type;
import org.example.domain.*;
import org.example.messages.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramController {
    private Bank bank;
    private Customer currentUser;
    private BankAccount currentBankAccount;
    private final Scanner sc = new Scanner(System.in);

    public ProgramController() {
        bank = new Bank();
    }

    public void startProgram() {
        while (true) {
            showMenu();
            int input = getUserIntegerInput();
            if (input == 4) {
                System.out.println("프로그램을 종료합니다.");
                return;
            }
            executeMenu(input);
        }
    }

    private void executeMenu(int input) {
        if (input == 1)  {
            showCustomerList();
        } else if (input == 2) {
            createNewCustomer();
        } else if (input == 3) {
            chooseUser();
            if (currentUser == null) return;
            while (true) {
                showUserServiceMenu();
                if (selectUserServiceMenu() == 1) break;
            }
        }
    }

    private int selectUserServiceMenu() {
        int userServiceInput = getUserIntegerInput();
        if (userServiceInput == 1) {
            BankAccount bankAccount = createNewBankAccount();
            if (bankAccount != null) {
                bank.addBankAccount(bankAccount);
                currentUser.addNewBankAccount(bankAccount);
            }
            checkCreatedBankAccountInfo(bankAccount);
        } else if (userServiceInput == 2) {
            viewBankAccountList();
        } else if (userServiceInput == 3) {
            chooseBankAccount();
            if (currentBankAccount == null) return 0;
            while (true) {
                showBankAccountServiceMenu();
                if (selectBankAccountServiceMenu() == 1) break;
            }
        } else if (userServiceInput == 4) {
            System.out.println("고객 서비스를 종료합니다.");
            return 1;
        }
        return 0;
    }

    private void checkCreatedBankAccountInfo(BankAccount bankAccount) {
        System.out.println("==============================");
        System.out.println("개설한 계좌 정보를 확인합니다.");
        System.out.println("계좌 번호 :" + bankAccount.getBankAccountNumber());
        System.out.println("계좌 이름 :" + bankAccount.getAlias());
        if (bankAccount instanceof Savings) {
            Savings savings = (Savings) bankAccount;
            System.out.println("계좌 만기일 :" + savings.getMaturityDate());
        }
        if (bankAccount instanceof KidsSavings) {
            KidsSavings kidsSavings = (KidsSavings) bankAccount;
            System.out.println("우대금리 :"+ kidsSavings.getMaturityDate());
        }
        if (bankAccount instanceof Pension) {
            Pension pension = (Pension) bankAccount;
            System.out.println("월 납입액 :" + pension.getMonthlyContribution());
        }
        if (bankAccount instanceof Securities) {
            Securities securities = (Securities) bankAccount;
            System.out.println("위험자산 비중 :" + securities.getRiskRatio());
        }
    }

    private void showBankAccountServiceMenu() {
        System.out.println("==============================");
        System.out.println(currentBankAccount.getAlias() + "계좌 서비스입니다. ");
        System.out.println("1. 입금");
        System.out.println("2. 출금");
        System.out.println("3. 이체");
        System.out.println("4. 결제");
        System.out.println("5. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    private int selectBankAccountServiceMenu() {
        int input = getUserIntegerInput();
        if (input == 1) {
            deposit();
        } else if (input == 2) {
            withdrawal();
        } else if (input == 3) {
            transfer();
        } else if (input == 4) {
            pay();
        } else if (input == 5) {
            System.out.println("계좌 서비스를 종료합니다.");
            return 1;
        }
        return 0;
    }

    private void pay() {
        System.out.println("==============================");
        System.out.println("두 건의 결제를 동시 진행합니다.");
        System.out.println("한 건의 결제 가격을 먼저 입력해주세요.");
        int price1 = getUserIntegerInput();
        System.out.println("그 다음 건의 결제 가격을 입력해주세요.");
        int price2 = getUserIntegerInput();

        Thread payment1 = new Thread(() -> currentBankAccount.pay(price1));
        Thread payment2 = new Thread(() -> currentBankAccount.pay(price2));

        payment1.start();
        payment2.start();
    }


    private void deposit() {
        System.out.println("==============================");
        System.out.println("보유 잔액: " + currentBankAccount.getBalance());
        System.out.println("입금할 금액을 입력해주세요.");
        int depositAmount = getUserIntegerInput();

        try {
            System.out.println("입금 중");
            Thread.sleep(2000);
            currentBankAccount.increaseBalance(depositAmount);
            System.out.println(depositAmount +"원이 입금되었습니다.");
            System.out.println("입금 후 잔액: " + currentBankAccount.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void withdrawal() {
        System.out.println("==============================");
        int balance = currentBankAccount.getBalance();
        System.out.println("출금 가능 잔액: " + balance);

        System.out.println("출금할 금액을 입력해주세요.");
        int withdrawalAmount = getUserIntegerInput();

        try {
            System.out.println("출금 중");
            Thread.sleep(2000);
            currentBankAccount.decreaseBalance(withdrawalAmount);
            System.out.println(withdrawalAmount +"원이 출금되었습니다.");
            System.out.println("출금 후 잔액: " + currentBankAccount.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void transfer() {
        System.out.println("==============================");
        List<BankAccount> bankAccountList = bank.getBankAccountList();
        if (bankAccountList.size() == 1 ) {
            System.out.println("송금 가능한 계좌가 없어 이체 서비스를 종료합니다.");
            return;
        }
        System.out.println("송금 가능한 계좌의 목록입니다.");
        System.out.println("계좌번호 계좌이름");
        for (BankAccount bankAccount : bankAccountList) {
            if (bankAccount.getBankAccountNumber() == currentBankAccount.getBankAccountNumber()) continue;
            System.out.println(bankAccount.getBankAccountNumber() + "  " + bankAccount.getAlias());
        }
        System.out.println("==============================");
        System.out.println("송금할 계좌의 계좌 번호를 입력해주세요.");
        System.out.println("0 입력 시 계좌 접속 종료");
        int bankAccountNumber = getUserIntegerInput();
        if (bankAccountNumber == 0) {
            System.out.println("이체를 취소합니다");
            return;
        }
        BankAccount remittanceDestination = bankAccountList.stream().filter(bankAccount -> bankAccount.getBankAccountNumber() == bankAccountNumber).findFirst().orElse(null);
        if (remittanceDestination == null) {
            System.out.println("잘못된 계좌 번호로 이체를 취소합니다.");
            return;
        }
        System.out.println(bankAccountNumber + "으로 송금할 금액을 입력해주세요.");
        System.out.println("0 입력 시 계좌 접속 종료");
        int remittanceAmount = getUserIntegerInput();
        if (remittanceAmount == 0) {
            System.out.println("이체를 취소합니다");
            return;
        }

        try {
            System.out.println("이체 중");
            Thread.sleep(2000);

            if (currentBankAccount.decreaseBalance(remittanceAmount)) {
                remittanceDestination.increaseBalance(remittanceAmount);
            }

            System.out.println("이체 완료");
            System.out.println("이체 후 잔액: " + currentBankAccount.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private BankAccount createNewBankAccount() {
        String type = currentUser.getType();
        showBankAccountMenu(type);
        while (true) {
            int input = getUserIntegerInput();
            if (input == 0) {
                System.out.println("계좌 개설을 중단합니다.");
                return null;
            } else if ((type.equals(Type.KID.getValue()) && input > 2) || (type.equals(Type.TEENAGER.getValue()) && input > 3) || (type.equals(Type.ADULT.getValue()) && input > 4)) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
                continue;
            }

            System.out.println("별칭을 입력해 주세요");
            String alias = sc.next();
            int bankAccountNumber = bank.getBankAccountList().size() + 1;
            if (input == 1) { // 입출금 계좌
                Checking checking = new Checking(bankAccountNumber);
                checking.setAlias(alias);
                return checking;
            } else if (input == 2) { // 적금 계좌
                Savings savings = new Savings(bankAccountNumber);
                savings.setAlias(alias);
                System.out.println("적금 만기일을 입력해주세요. (YYYYMMDD 형식)");
                String maturityDate = sc.next();
                savings.setMaturityDate(maturityDate);
                return savings;
            } else if (!type.equals(Type.KID.getValue()) && input == 3) { // 연금 계좌
                Pension pension = new Pension(bankAccountNumber);
                pension.setAlias(alias);
                System.out.println("월 납입액을 입력해주세요.");
                int monthlyContribution = getUserIntegerInput();
                pension.setMonthlyContribution(monthlyContribution);
                return pension;
            } else if (type.equals(Type.KID.getValue()) && input == 3) { // 어린이 적금 계좌
                KidsSavings kidsSavings = new KidsSavings(bankAccountNumber);
                kidsSavings.setAlias(alias);
                System.out.println("적금 만기일을 입력해주세요. (YYYYMMDD 형식)");
                String maturityDate = sc.next();
                kidsSavings.setMaturityDate(maturityDate);
                System.out.println("우대 금리를 입력해주세요. 형식)");
                double bonusRate = getUserDoubleInput();
                kidsSavings.setBonusRate(bonusRate);
                return kidsSavings;
            } else if (input == 4) { // 증권 계좌
                Securities securities = new Securities(bankAccountNumber);
                securities.setAlias(alias);
                System.out.println("위험 자산 비중을 입력해주세요.");
                double riskAssetRation = getUserDoubleInput();
                securities.setRiskAssetRation(riskAssetRation);
                return securities;
            }
        }
    }

    private void viewBankAccountList() {
        List<BankAccount> bankAccountList = currentUser.getBankAccountList();
        System.out.println("==============================");
        if (bankAccountList.size() == 0 ) {
            System.out.println("개설된 계좌 정보가 없습니다.");
            return;
        }
        System.out.println("개설된 계좌의 리스트입니다.");
        System.out.println("계좌번호 계좌이름");
        for (BankAccount bankAccount : bankAccountList) {
            System.out.println(bankAccount.getBankAccountNumber() + "  " + bankAccount.getAlias());
        }
    }

    private void chooseBankAccount() {
        List<BankAccount> bankAccountList = currentUser.getBankAccountList();
        if (bankAccountList.size() == 0) {
            System.out.println("이용할 수 있는 계좌가 없습니다.");
            System.out.println("계좌를 먼저 개설해 주세요.");
            return;
        }
        System.out.println("==============================");
        System.out.println("  계좌번호     계좌명");
        for (BankAccount bankAccount : bankAccountList) {
            System.out.println(bankAccount.getBankAccountNumber() + "    " + bankAccount.getAlias());
        }
        System.out.println("==============================");

        while (true) {
            System.out.println("계좌 번호를 입력해주세요");
            System.out.println("0 입력 시 계좌 접속 종료");
            int input = getUserIntegerInput();
            if (input == 0) {
                System.out.println("이용 계좌 선택을 종료합니다");
                return;
            }
            for (int i = 0; i < bankAccountList.size(); i++) {
                if (bankAccountList.get(i).getBankAccountNumber() == input) {
                    currentBankAccount = bankAccountList.get(i);
                    return;
                }
            }
            System.out.println("유효하지 않은 입력 값입니다.");
        }
    }

    private void showBankAccountMenu(String type) {
        System.out.println("==============================");
        System.out.println("======  새 계좌를 개설합니다 =======");
        System.out.println("개설할 계좌 타입을 선택해 주세요.");
        System.out.println("0. 계좌 개설 중단");
        System.out.println("1. 입출금 계좌");
        System.out.println("2. 적금 계좌");

        if (!type.equals(Type.KID.getValue())) {
            System.out.println("3. 연금 계좌");
        }

        if (type.equals(Type.KID.getValue())) {
            System.out.println("3. 어린이 적금 계좌");
        }

        if (type.equals(Type.ADULT.getValue())) {
            System.out.println("4. 증권 계좌");
        }
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    private void showMenu() {
        System.out.println("==============================");
        System.out.println("1. 고객 리스트 확인 ");
        System.out.println("2. 고객 추가");
        System.out.println("3. 로그인 할 고객 선택");
        System.out.println("4. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    private void chooseUser() {
        List<Customer> customerList = Bank.getCustomerList();
        if (customerList.size() == 0) {
            System.out.println("접속할 수 있는 고객 계정이 없습니다.");
            System.out.println("고객 계정을 먼저 생성해 주세요.");
            return;
        }
        System.out.println("==============================");
        System.out.println("  id     이름");
        for (Customer customer : customerList) {
            System.out.println("" + customer.getId() + "    " + customer.getName());
        }
        System.out.println("==============================");
        while (true) {
            System.out.println("고객 id를 입력해주세요");
            System.out.println("0 입력 시 고객 계정 접속 종료");
            int input = getUserIntegerInput();
            if (input == 0) {
                System.out.println("접속 고객 선택을 종료합니다");
                return;
            }
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getId() == input) {
                    currentUser = customerList.get(i);
                    return;
                }
            }
            System.out.println("유효하지 않은 입력 값입니다.");
        }
    }

    private void showUserServiceMenu() {
        System.out.println("==============================");
        System.out.println(currentUser.getName() +"님 안녕하세요.");
        System.out.println("1. 새 계좌 개설 ");
        System.out.println("2. 계좌 리스트 확인");
        System.out.println("3. 이용할 계좌 선택");
        System.out.println("4. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    private void createNewCustomer() {
        Customer customer = new Customer();
        System.out.println("==============================");
        System.out.println("새 고객 정보를 등록합니다.");
        System.out.println("고객 이름을 입력해주세요");
        String name = sc.next();
        System.out.println("고객 나이를 입력해주세요");
        int age = getUserIntegerInput();
        customer.setName(name);
        customer.setAge(age);
        List<Customer> customerList = bank.getCustomerList();
        customer.setId(customerList.size() + 1);
        bank.addCustomer(customer);
    }

    private void showCustomerList() {
        List<Customer> customerList = Bank.getCustomerList();
        if (customerList.size() == 0 ) {
            System.out.println("등록된 고객 정보가 없습니다.");
            return;
        }
        System.out.println("==============================");
        System.out.println(" id     이름");
        for (Customer customer : customerList) {
            System.out.println("" + customer.getId() + "    " + customer.getName());
        }
        System.out.println("==============================");
    }

    private Integer getUserIntegerInput() {
        String input;
        while (true) {
            try {
                if (!sc.hasNextInt()) { System.err.println("필요한 정수 입력 없음"); return -1; }
                input = sc.next();
                return Integer.parseInt(input);
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }
    }

    private double getUserDoubleInput() {
        String input;
        while (true) {
            try {
                if (!sc.hasNextDouble()) { System.err.println("필요한 소수 입력이 없음"); return -1; }
                input = sc.next();
                return Double.parseDouble(input);
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }
    }
}
