package br.net.mirante.singular.util.wicket.resource;

public enum Icone {
    BAN /*              */("icon-ban"),
    BRIEFCASE /*        */("icon-briefcase"),
    CALENDAR /*         */("icon-calendar"),
    CAMERA /*           */("icon-camera"),
    CARET_SQUARE /*     */("fa fa-caret-square-o-up"),
    CHAIN /*            */("fa fa-chain"),
    CHECK /*            */("fa fa-check"),
    CHECK_CIRCLE /*     */("fa-check-circle-o"),
    COGS /*             */("fa fa-cogs"),
    CREDIT_CARD /*      */("icon-credit-card"),
    CUP /*              */("icon-cup"),
    DIRECTIONS /*       */("icon-directions"),
    EXTERNAL_LINK /*    */("fa fa-external-link"),
    EYE /*              */("fa fa-eye"),
    FILE_POWERPOINT /*  */("fa fa-file-powerpoint-o"),
    FILE_PDF /*         */("fa fa-file-pdf-o"),
    FILE_TEXT /*        */("fa fa-file-text"),
    GIFT /*             */("fa fa-gift"),
    GLOBE /*            */("fa fa-globe"),
    GRID /*             */("icon-grid"),
    HEART/*             */("fa fa-heart"),
    INFO_CIRCLE /*      */("fa fa-info-circle"),
    LIST/*              */("fa fa-list"),
    LIST_ALT/*          */("fa fa-list-alt"),
    LOCK/*              */("fa fa-lock"),
    MAP_MARKER /*       */("fa fa-map-marker"),
    MONEY /*            */("fa fa-money"),
    PENCIL_SQUARE /*    */("fa fa-pencil-square-o"),
    PIN /*              */("icon-pin"),
    SHARE_ALT /*        */("fa fa-share-alt"),
    SHARE_SQUARE /*     */("fa fa-share-square-o"),
    SPEECH /*           */("icon-speech"),
    USER /*             */("fa fa-user"),
    USERS /*            */("icon-users"),
    USERS3 /*           */("fa fa-users"),
    TAG /*              */("icon-tag"),
    TAGS /*             */("fa fa-tags"),
    TIMES /*            */("fa fa-times"),
    VERTICAL_ELLIPSIS /**/("fa fa-ellipsis-v"),
    WALLET /*           */("icon-wallet"),
    HOME/*              */("icon-home"),
    STAR/*              */("icon-star"),
    HOTEL/*             */("fa fa-h-square");
    

    private final String cssClass;

    private Icone(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
    @Override
    public String toString() {
        return getCssClass();
    }
}