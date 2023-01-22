import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFornecedor } from '../fornecedor.model';
import { FornecedorService } from '../service/fornecedor.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fornecedor-delete-dialog.component.html',
})
export class FornecedorDeleteDialogComponent {
  fornecedor?: IFornecedor;

  constructor(protected fornecedorService: FornecedorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fornecedorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
