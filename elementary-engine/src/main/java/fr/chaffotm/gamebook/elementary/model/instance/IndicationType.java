package fr.chaffotm.gamebook.elementary.model.instance;

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
    }, EVENT {
        @Override
        public String toString() {
            return "event";
        }
    }
}
