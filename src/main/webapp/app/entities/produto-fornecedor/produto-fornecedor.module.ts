import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProdutoFornecedorComponent } from './list/produto-fornecedor.component';
import { ProdutoFornecedorDetailComponent } from './detail/produto-fornecedor-detail.component';
import { ProdutoFornecedorUpdateComponent } from './update/produto-fornecedor-update.component';
import { ProdutoFornecedorDeleteDialogComponent } from './delete/produto-fornecedor-delete-dialog.component';
import { ProdutoFornecedorRoutingModule } from './route/produto-fornecedor-routing.module';

@NgModule({
  imports: [SharedModule, ProdutoFornecedorRoutingModule],
  declarations: [
    ProdutoFornecedorComponent,
    ProdutoFornecedorDetailComponent,
    ProdutoFornecedorUpdateComponent,
    ProdutoFornecedorDeleteDialogComponent,
  ],
})
export class ProdutoFornecedorModule {}
