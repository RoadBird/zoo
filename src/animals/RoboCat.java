package animals;

public class RoboCat extends Feline {
    private static int counter = 0;
    private static final double CAT_SIZE = 50;
    private VoiceModule voiceModule = new VoiceModule("Beep");

    public RoboCat() {
        super(CAT_SIZE, "RoboCat #" + counter++);
        type = "RoboCat";
    }

    public VoiceModule getVoiceModule() {
        return voiceModule;
    }

    public void setVoiceModule(VoiceModule voiceModule) {
        this.voiceModule = voiceModule;
    }

    @Override
    public double jump() {
        return 100;
    }

    @Override
    public void sound() {
        System.out.println(getVoiceModule().generateSound());
    }

    public static class VoiceModule {
        private String voice = "00000000";

        public VoiceModule(String voice){
            this.voice = voice;
        }

        public String generateSound() {
            return voice;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }
    }
}
