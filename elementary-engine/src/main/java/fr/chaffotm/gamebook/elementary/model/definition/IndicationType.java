package fr.chaffotm.gamebook.elementary.model.definition;

public enum IndicationType {
    CLUE {
        @Override
        public String toString() {
            return "clue";
        }
    }, DEDUCTION {
        @Override
        public String toString() {
            return "deduction";
        }
    }, DECISION {
        @Override
        public String toString() {
            return "decision";
        }
    }
}
