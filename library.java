import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issue() {
        isIssued = true;
    }

    public void returnBook() {
        isIssued = false;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Issued: " + isIssued;
    }
}

class Member {
    private String id;
    private String name;
    private Map<String, LocalDate> issuedBooks;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.issuedBooks = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, LocalDate> getIssuedBooks() {
        return issuedBooks;
    }

    public void issueBook(String bookId, LocalDate issueDate) {
        issuedBooks.put(bookId, issueDate);
    }

    public void returnBook(String bookId) {
        issuedBooks.remove(bookId);
    }

    @Override
    public String toString() {
        return "Member ID: " + id + ", Name: " + name + ", Issued Books: " + issuedBooks;
    }
}

class LibraryManagementSystem {
    private List<Book> books;
    private List<Member> members;
    private static final int DUE_DAYS = 14;
    private static final double LATE_FEE_PER_DAY = 1.0;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book);
    }

    public void registerMember(Member member) {
        members.add(member);
        System.out.println("Member registered: " + member);
    }

    public void issueBook(String bookId, String memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book != null && member != null && !book.isIssued()) {
            book.issue();
            member.issueBook(bookId, LocalDate.now());
            System.out.println("Book issued: " + book);
        } else {
            System.out.println("Issue operation failed.");
        }
    }

    public void returnBook(String bookId, String memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book != null && member != null && book.isIssued()) {
            LocalDate issueDate = member.getIssuedBooks().get(bookId);
            LocalDate returnDate = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(issueDate, returnDate);

            if (daysBetween > DUE_DAYS) {
                double penalty = (daysBetween - DUE_DAYS) * LATE_FEE_PER_DAY;
                System.out.println("Late return penalty: $" + penalty);
            }

            book.returnBook();
            member.returnBook(bookId);
            System.out.println("Book returned: " + book);
        } else {
            System.out.println("Return operation failed.");
        }
    }

    public void viewAvailableBooks() {
        for (Book book : books) {
            if (!book.isIssued()) {
                System.out.println(book);
            }
        }
    }

    public void searchBooks(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println(book);
            }
        }
    }

    public void searchMembers(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                System.out.println(member);
            }
        }
    }

    private Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Available Books");
            System.out.println("6. Search Books");
            System.out.println("7. Search Members");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter book ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(new Book(bookId, title, author));
                    break;
                case "2":
                    System.out.print("Enter member ID: ");
                    String memberId = scanner.nextLine();
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    library.registerMember(new Member(memberId, name));
                    break;
                case "3":
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextLine();
                    library.issueBook(bookId, memberId);
                    break;
                case "4":
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextLine();
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextLine();
                    library.returnBook(bookId, memberId);
                    break;
                case "5":
                    library.viewAvailableBooks();
                    break;
                case "6":
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    library.searchBooks(title);
                    break;
                case "7":
                    System.out.print("Enter member name: ");
                    name = scanner.nextLine();
                    library.searchMembers(name);
                    break;
                case "8":
                    scanner.close();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}