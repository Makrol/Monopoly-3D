package com.ggg.monopoly;

public class SpecialCard {
    public enum fromOp {noAction,fromEveryone,fromMe,fromBank};
    public enum toOp {noAction,toMe,toBank};
    public enum action {noAction,moveForward,goStart};
    private String title;
    private Integer moneyChange;
    private fromOp moneyFrom;
    private toOp moneyTo;
    private action cardAction;

    /**
     * Konstruktor ladujacy dane do nowo utworzonego obiektu
     * @param title tytul karty
     * @param moneyChange wartosc o jaka ma sie zmienic stan pieniedzy
     * @param moneyFrom wartosc skad ma byc wyslana kwota
     * @param moneyTo  wartosc dokad ma byc wyslana kwota
     * @param cardAction akcja karty
     */
    public SpecialCard(String title, Integer moneyChange, fromOp moneyFrom, toOp moneyTo, action cardAction) {
        this.title = title;
        this.moneyChange = moneyChange;
        this.moneyFrom = moneyFrom;
        this.moneyTo = moneyTo;
        this.cardAction = cardAction;
    }


    /**
     * Zwraca tytul karty
     * @return tytul karty
     */
    public String getTitle() {
        return title;
    }

    /**
     * Zwraca wartosc o jaka ma sie zmienic stan pieniedzy
     * @return wartosc o jaka ma sie zmienic stan pieniedzy
     */
    public Integer getMoneyChange() {
        return moneyChange;
    }

    /**
     * Zwraca wartosc skad ma byc wyslana kwota
     * @return wartosc skad ma byc wyslana kwota
     */
    public fromOp getMoneyFrom() {
        return moneyFrom;
    }

    /**
     * Zwraca wartosc dokad ma byc wyslana kwota
     * @return wartosc dokad ma byc wyslana kwota
     */
    public toOp getMoneyTo() {
        return moneyTo;
    }

    /**
     * Zwraca akcje karty
     * @return akcje karty
     */
    public action getCardAction() {
        return cardAction;
    }
}
