package sfwe302.assignment8.part1;

public class IceCreamProtocol {
    private enum State { FLAVOR, AMOUNT, PAYMENT, WAITING  }
    private State state = State.FLAVOR;
    private int amount_unpaid = 0;
    private int amount_paid = 0;

    public String processInput(String input) {
        String output = null;

        switch (state) {
            case FLAVOR:
                output = "Hey, what Ice Cream can I offer?";
                state = State.AMOUNT;
                break;
            case AMOUNT:
                if (input.toLowerCase().contains("vanilla") | input.toLowerCase().contains("chocolate")) {
                    output = "$2, please";
                    amount_unpaid = 2;
                    state = State.PAYMENT;
                } else if (input.toLowerCase().contains("lemon")) {
                    output = "$1, please";
                    amount_unpaid = 1;
                    state = State.PAYMENT;
                }
                else { state = State.FLAVOR; }
                break;
            case PAYMENT:
                try {
                    int payment = Integer.parseInt(input.replaceAll("[^\\d.]", "")) + amount_paid;
                    amount_paid = 0;

                    if (payment > amount_unpaid) {
                        output = "Here is $" + (payment - amount_unpaid) + " back. Here you are! Thank you";
                        state = State.WAITING;
                    } else if (payment < amount_unpaid) {
                        output = "Not enough, $" + (amount_unpaid - payment) + " please";
                        amount_paid = payment;
                    } else {
                        output = "Great you have an exact. Here you are! Thank you";
                        state = State.WAITING;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

                break;
            case WAITING:
                if (!input.toLowerCase().contains("bye")) {
                    state = State.FLAVOR;
                }
                break;
        }

        return output;
    }
}
