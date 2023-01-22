import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdutoFornecedor } from '../produto-fornecedor.model';
import { ProdutoFornecedorService } from '../service/produto-fornecedor.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './produto-fornecedor-delete-dialog.component.html',
})
export class ProdutoFornecedorDeleteDialogComponent {
  produtoFornecedor?: IProdutoFornecedor;

  constructor(protected produtoFornecedorService: ProdutoFornecedorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.produtoFornecedorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
