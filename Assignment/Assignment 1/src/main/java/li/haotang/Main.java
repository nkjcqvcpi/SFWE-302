package li.haotang;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Get the current process ID
        long processId = ProcessHandle.current().pid();

        // Print the process ID
        System.out.println("Process ID: " + processId + ", Argument: " + args[0]);
    }
}