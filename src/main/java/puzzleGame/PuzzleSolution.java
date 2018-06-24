package puzzleGame;

public class PuzzleSolution {
        private boolean solutionExists;
        private String [] errors;
        private Solution solution;

        public PuzzleSolution(boolean solutionExists) {
            this.solutionExists = solutionExists;
        }

        public void setErrors(String [] errors){
            this.errors = errors;
        }

        public void setSolution(Solution solution) {
            this.solution = solution;
        }

}
