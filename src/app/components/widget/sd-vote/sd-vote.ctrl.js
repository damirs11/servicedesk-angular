import {NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class SDVoteController {
    commentText = "";
    busy = false;
    error;

    get isButtonRefuseActive() {
        return Boolean(this.commentText && this.commentText.length);
    }

    async makeVote(approved){
        if (this.busy) return;
        this.busy = "saving vote";
        this.vote.reason = this.commentText;
        this.vote.approved = approved;
        try {
            await this.vote.save();
            this.onVote({$vote: this.vote});
        } catch (e) {
            this.error = e;
        }
        this.busy = false;
    }
}

export {SDVoteController as controller}