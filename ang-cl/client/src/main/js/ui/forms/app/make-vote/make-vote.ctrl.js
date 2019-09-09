import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class MakeVoteModalController {

    @NGInject() $modalState;
    @NGInject() SD;
    @NGInject("vote") providedVote;
    vote;

    $onInit(){
        this.$modalState.onCancel = this.cancel;
        this.vote = new this.SD.ApproverVote(this.providedVote.id);
    }

    cancel() {
        this.$modalState.resolve(null)
    }

    voteYes() {
        this.vote.approved = 1;
        this.$modalState.resolve(this.vote);
    }

    voteNo() {
        this.vote.approved = 0;
        this.$modalState.resolve(this.vote);
    }

}

export {MakeVoteModalController as controller}