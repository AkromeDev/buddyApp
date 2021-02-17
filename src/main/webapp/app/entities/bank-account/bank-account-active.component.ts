import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBankAccount } from '@/shared/model/bank-account.model';
import BankAccountService from './bank-account.service';

@Component
export default class BankAccountActive extends Vue {
  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;
  public bankAccount: IBankAccount = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
        vm.getBankAccount();
    });
  }

  public getBankAccount() {
    this.bankAccountService()
      .get()
      .then(res => {
        this.bankAccount = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
