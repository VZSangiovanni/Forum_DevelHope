package co.develhope.forum.model;

    //classe senza annotations non trattate dal framework
//results della query

    public class Topic {

        private int idForumTopic;
        private String title;
        private String text;
        private float creation;

        private int idCategory;
        private int idUser;

        public void setIdForumTopic(int idForumTopic) {
            this.idForumTopic = idForumTopic;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setCreation(float creation) {
            this.creation = creation;
        }

        public void setIdCategory(int idCategory) {
            this.idCategory = idCategory;
        }

        public void setIdUser(int idUser) {
            this.idUser = idUser;
        }

        public int getIdForumTopic() {
            return idForumTopic;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public float getCreation() {
            return creation;
        }

        public int getIdCategory() {
            return idCategory;
        }

        public int getIdUser() {
            return idUser;
        }

        @Override
        public String toString() {
            return "Topic{" +
                    "idForumTopic=" + idForumTopic +
                    ", title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    ", creation=" + creation +
                    ", idCategory=" + idCategory +
                    ", idUser=" + idUser +
                    '}';
        }
    }
