package com.ggg.monopoly;

public class SpecialCard {
    public enum fromOp {noAction,fromEveryone,fromMe,fromBank};
    public enum toOp {noAction,toMe,toBank};
    public enum action {noAction,moveBack,moveForward,goStart};
    private String title;
    private Integer moneyChange;
    private fromOp moneyFrom;
    private toOp moneyTo;

    private action cardAction;


    public SpecialCard(String title, Integer moneyChange, fromOp moneyFrom, toOp moneyTo, action cardAction) {
        this.title = title;
        this.moneyChange = moneyChange;
        this.moneyFrom = moneyFrom;
        this.moneyTo = moneyTo;
        this.cardAction = cardAction;
    }


    public String getTitle() {
        return title;
    }


    public Integer getMoneyChange() {
        return moneyChange;
    }

    public fromOp getMoneyFrom() {
        return moneyFrom;
    }

    public toOp getMoneyTo() {
        return moneyTo;
    }

    public action getCardAction() {
        return cardAction;
    }
}
