package  SPLITWISE;

import java.util.List;

public class Split {

    User user;
    double amountOwe;

    public Split(User user, double amountOwe){
        this.user = user;
        this.amountOwe = amountOwe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmountOwe() {
        return amountOwe;
    }

    public void setAmountOwe(double amountOwe) {
        this.amountOwe = amountOwe;
    }

}
interface ExpenseSplit {

    public void validateSplitRequest(List<Split> splitList, double totalAmount);
}

class EqualExpenseSplit implements ExpenseSplit{

    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {

        //validate total amount in splits of each user is equal and overall equals to totalAmount or not
        double amountShouldBePresent = totalAmount/splitList.size();
        for(Split split: splitList) {
           if(split.getAmountOwe() != amountShouldBePresent) {
               //throw exception
           }
        }
    }
}

class PercentageExpenseSplit implements ExpenseSplit {
    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {

    }
}

class UnequalExpenseSplit implements ExpenseSplit{

    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {

    }
}

