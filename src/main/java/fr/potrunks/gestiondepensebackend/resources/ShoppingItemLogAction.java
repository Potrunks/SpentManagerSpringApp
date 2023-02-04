package fr.potrunks.gestiondepensebackend.resources;

public enum ShoppingItemLogAction {

    ADDED{
        @Override
        public String toString() {
            return "ADDED";
        }
    },
    MODIFIED,
    DELETED,
    BOUGHT
}
