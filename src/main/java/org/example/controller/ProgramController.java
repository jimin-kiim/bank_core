package org.example.controller;

import org.example.constants.Type;
import org.example.domain.*;
import org.example.messages.ErrorMessage;

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
            int input = getUserInput();
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
        int userServiceInput = getUserInput();
        if (userServiceInput == 1) {
            BankAccount bankAccount = createNewBankAccount();
            if (bankAccount != null) {
                currentUser.addNewBankAccount(bankAccount);
            }
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

    private void showBankAccountServiceMenu() {
        System.out.println("==============================");
        System.out.println(currentBankAccount.getAlias() + "계좌 서비스입니다. ");
        System.out.println("1. 입금 ");
        System.out.println("2. 출금");
        System.out.println("3. 이체");
        System.out.println("4. 프로그램 종료");
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    private int selectBankAccountServiceMenu() {
        int input = getUserInput();
        if (input == 1) {
            deposit();
        } else if (input == 2) {
            withdrawal();
        } else if (input == 3) {
            transfer();
        } else if (input == 4) {
            System.out.println("계좌 서비스를 종료합니다.");
            return 1;
        }
        return 0;
    }

    private void deposit() {
        System.out.println("==============================");
        System.out.println("보유 잔액: " + currentBankAccount.getBalance());
        System.out.println("입금할 금액을 입력해주세요.");
        int depositAmount = getUserInput();

        try { // 임계 영역
            System.out.println("입금 중");
            Thread.sleep(200);
            currentBankAccount.increaseBalance(depositAmount);
            System.out.println(depositAmount +"원이 입금되었습니다.");
            System.out.println("입금 후 잔액: " + currentBankAccount.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void withdrawal() {
        System.out.println("==============================");
        System.out.println("출금 가능 잔액: " + currentBankAccount.getBalance());
        System.out.println("출금할 금액을 입력해주세요.");
        int withdrawalAmount = getUserInput();

        try {
            System.out.println("출금 중");
            Thread.sleep(200);
            currentBankAccount.decreaseBalance(withdrawalAmount);
            System.out.println(withdrawalAmount +"원이 출금되었습니다.");
            System.out.println("출금 후 잔액: " + currentBankAccount.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void transfer() {

    }

    private BankAccount createNewBankAccount() {
        String type = currentUser.getType();
        showBankAccountMenu(type);
        while (true) {
            int input = getUserInput();
            if (input == 0) {
                System.out.println("계좌 개설을 중단합니다.");
                return null;
            } else if ((type.equals(Type.KID.getValue()) && input > 2 )|| (type.equals(Type.TEENAGER.getValue()) && input > 3) || (type.equals(Type.ADULT.getValue()) && input > 4 )) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
                continue;
            }

            System.out.println("별칭을 입력해 주세요");
            String alias = getUserStringInput();
            if (input == 1) {
                Checking checking =  new Checking();
                checking.setAlias(alias);
                return checking;
            } else if (input == 2) {
                Savings savings = new Savings();
                savings.setAlias(alias);
                return savings;
            } else if (input == 3) {
                Pension pension = new Pension();
                pension.setAlias(alias);
                return pension;
            } else if (input == 4) {
                Securities securities = new Securities();
                securities.setAlias(alias);
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
            System.out.println("" + bankAccount.getBankAccountNumber() + "    " + bankAccount.getAlias());
        }
        System.out.println("==============================");

        while (true) {
            System.out.println("계좌 번호를 입력해주세요");
            System.out.println("0 입력 시 계좌 접속 종료");
            int input = getUserInput();
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
        if (type.equals(Type.ADULT.getValue())) {
            System.out.println("4. 증권 계좌");
        }
        System.out.println("==============================");
        System.out.println("실행할 메뉴를 선택해 주세요");
    }

    private Integer getUserInput() {
        String input;
        while (true) {
            try {
                input = sc.next();
                return Integer.parseInt(input);
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }
    }

    private String getUserStringInput() {
        return sc.next();
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
            int input = getUserInput();
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
        String name = getUserStringInput();
        System.out.println("고객 나이를 입력해주세요");
        int age = getUserInput();
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
}
