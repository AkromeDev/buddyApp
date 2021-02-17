import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBuddy } from '@/shared/model/buddy.model';
import BuddyService from './buddy.service';

@Component
export default class BuddyDetails extends Vue {
  @Inject('buddyService') private buddyService: () => BuddyService;
  public buddy: IBuddy = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.buddyId) {
        vm.retrieveBuddy(to.params.buddyId);
      }
    });
  }

  public retrieveBuddy(buddyId) {
    this.buddyService()
      .find(buddyId)
      .then(res => {
        this.buddy = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
