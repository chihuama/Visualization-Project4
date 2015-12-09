public enum DataFile 
{	
data2010(0) {
        @Override
        public String filename() {
                return "outputdata2010.csv";
        }
        @Override
        public String label() {
                return "2010 Crash Data";
        }
},
data2009(1) {
    @Override
    public String filename() {
            return "outputdata2009.csv";
    }
    @Override
    public String label() {
            return "2009 Crash Data";
    }

},data2008(2) {
         @Override
         public String filename() {
                 return "outputdata2008.csv";
         }
         @Override
         public String label() {
                 return "2008 Crash Data";
         }

 }, data2007(3) {

         @Override
         public String filename() {
                 return "outputdata2007.csv";
         }
         @Override
         public String label() {
                 return "2007 Crash Data";
         }

 }, data2006(4) {
         @Override
         public String filename() {
                 return "outputdata2006.csv";
         }
         @Override
         public String label() {
                 return "2006 Crash Data";
         }

 }, data2005(5) {
         @Override
         public String filename() {
                 return "outputdata2005.csv";
         }
         @Override
         public String label() {
                 return "2005 Crash Data";
         }
 },
 data2004(6) {
     @Override
     public String filename() {
             return "outputdata2004.csv";
     }
     @Override
     public String label() {
             return "2004 Crash Data";
     }
},
data2003(7) {
    @Override
    public String filename() {
            return "outputdata2003.csv";
    }
    @Override
    public String label() {
            return "2003 Crash Data";
    }
},
data2002(8) {
    @Override
    public String filename() {
            return "outputdata2002.csv";
    }
    @Override
    public String label() {
            return "2002 Crash Data";
    }
},
data2001(9) {
    @Override
    public String filename() {
            return "outputdata2001.csv";
    }
    @Override
    public String label() {
            return "2001 Crash Data";
    }
},
percentFatalities(10) {
    @Override
    public String filename() {
            return "percentStates.csv";
    }
    @Override
    public String label() {
            return "State Percent";
    }
};
 private int value;

 public abstract String filename();
 public abstract String label();
 
 private DataFile(int value) {

         this.value = value;

 }

}
