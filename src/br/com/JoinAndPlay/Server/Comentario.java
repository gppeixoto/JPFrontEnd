package br.com.JoinAndPlay.Server;



public class Comentario {
        private String text;
        private String user_name;
        private String user_id;
        private String user_photo;
        private String hour;
        private String date;
       
        public Comentario(String text, String user_name, String user_id, String user_photo, String hour, String date) {
                this.text = text;
                this.user_name = user_name;
                this.user_id = user_id;
                this.user_photo = user_photo;
                this.hour = hour;
                this.date = date;
        }
       
        /**
         * @return o conteudo do comentario.
         * */
        public String getText() { return this.text; }
       
        /**
         * @return o nome do usuario que comentou.
         * */
        public String getUserName() { return this.user_name; }
       
        /**
         * @return o id do usuario que comentou.
         * */
        public String getId() { return this.user_id; }
       
        /**
         * @return a hora que o comentario foi feito.
         * */
        public String getHour() { return this.hour; }
       
        /**
         * @return o dia que o comentario foi feito.
         * */
        public String getDate() { return this.date; }
       
        /**
         * @return a foto do usuario.
         * */
        public String getPhoto() { return this.user_photo; }
}

